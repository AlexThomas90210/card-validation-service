package teame.services;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MqttNotificationService implements INotificationService {

    private MqttClient mqttPublisher = null;

    /**
     * Default constructor - required for serialization purposes.
     *
     * Note: This ctor is not intended to be used directly.
     */
    public MqttNotificationService(){
    }


    /**
     * Initialize a connection with the given MQTT Broker
     *
     * @param strMqttMessageBroker MQTT Message Broker host details - e.g. "tcp://localhost:1883"
     */
    public void initialize(final String strMqttMessageBroker)
    {
        /*
         * Create our MQTT subscriber
         */

        System.out.println("Establishing connection with the MQTT Broker=" + strMqttMessageBroker + "...");

        // each MQTT client needs a unique identifier
        String strMqttPublisherId = UUID.randomUUID().toString();

        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try
        {
            this.mqttPublisher = new MqttClient(strMqttMessageBroker, strMqttPublisherId, memoryPersistence);
        }
        catch (MqttException ex)
        {
            ex.printStackTrace();
        }

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        try {
            mqttPublisher.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        System.out.println("Connected MQTT Publisher as ClientID=" + strMqttPublisherId + ".");
    }

    /**
     * Publish a message on the given topic to the MQTT Broker
     *
     * @param strMqttTopic MQTT Topic to publish to
     * @param strMessage Message to publish
     */
    public void publish(final String strMqttTopic, final String strMessage) {
        System.out.println("Publishing message='" + strMessage + "' on topic='" + strMqttTopic + "'...");
        try {
            MqttMessage mqttMessage = new MqttMessage(strMessage.getBytes());
            mqttPublisher.publish(strMqttTopic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        System.out.println("Published message.");
    }

    /**
     * Terminate the MQTT client connection.
     *
     */
    public void disconnect() {
        try {
            this.mqttPublisher.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

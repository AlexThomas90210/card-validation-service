package teame.services;

public interface INotificationService {
    /**
     * Initialize a connection with the given MQTT Broker
     *
     * @param strMqttMessageBroker MQTT Message Broker host details - e.g. "tcp://localhost:1883"
     */
    void initialize(final String strMqttMessageBroker);

    /**
     * Publish a message on the given topic to the MQTT Broker
     *
     * @param strMqttTopic MQTT Topic to publish to
     * @param strMessage Message to publish
     */
    void publish(final String strMqttTopic, final String strMessage);

    /**
     * Disconnect from the MQTT Broker.
     */
    void disconnect();
}

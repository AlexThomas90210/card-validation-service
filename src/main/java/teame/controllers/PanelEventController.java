package teame.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teame.services.IEventValidationService;
import teame.models.EventValidationRequest;
import teame.models.EventValidationResponse;

/**
 * Rest Controller implementation for the Panel API
 */
@RestController
public class PanelEventController implements IPanelEventController {

    private final IEventValidationService eventValidationService;

    PanelEventController(IEventValidationService eventValidationService){
        this.eventValidationService = eventValidationService;
    }

    @RequestMapping(value = "api/panel/request", method = RequestMethod.GET)
    public EventValidationResponse validateRequest(
            @RequestParam(value = "panelid", required = true) String panelid,
            @RequestParam(value = "cardid", required = true) String cardid,
            @RequestParam(value = "allowed", required = true) Boolean allowed)
    {
        EventValidationRequest request = new EventValidationRequest(allowed, cardid, panelid);
        return eventValidationService.validateRequest(request);
    }
}

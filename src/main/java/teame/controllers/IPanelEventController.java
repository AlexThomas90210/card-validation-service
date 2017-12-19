package teame.controllers;

import org.springframework.web.bind.annotation.RequestMethod;
import teame.models.EventValidationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Rest Interface for the system
 */
public interface IPanelEventController {

    @RequestMapping(value = "api/panel/request", method = RequestMethod.GET)
    EventValidationResponse validateRequest(
            @RequestParam(value = "panelid", required = true) String panelid,
            @RequestParam(value = "cardid", required = true) String cardid,
            @RequestParam(value = "allowed", required = true) Boolean allowed);
}

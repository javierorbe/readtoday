package dev.team.readtoday.server.shared.infrastructure.controller;

import jakarta.ws.rs.core.SecurityContext;

public enum BaseControllerBridge {
  ;

  public static void setSecurityContext(BaseController controller,
                                        SecurityContext securityContext) {
    controller.setSecurityContext(securityContext);
  }
}

package com.clianz.demo.akkahttp

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives

class PingService extends Directives {

  def route =
    path("ping") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "Pong"))
      }
    }
}

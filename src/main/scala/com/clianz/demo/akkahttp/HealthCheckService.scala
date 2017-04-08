package com.clianz.demo.akkahttp

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import spray.json.DefaultJsonProtocol

case class ServerStatus(status: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val serverStatusFormat = jsonFormat1(ServerStatus)
}

class HealthCheckService extends Directives with JsonSupport {

  def route =
    path("health") {
      get {
        complete(ServerStatus("up"))
      }
    }
}

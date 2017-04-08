package com.clianz.demo.akkahttp

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.Directives._

import scala.util.{Failure, Success}

object WebServer {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("routes")
    implicit val mat = ActorMaterializer()
    implicit val ec = system.dispatcher

    val log = Logging(system, this.getClass)
    val conf = ConfigFactory.load()
    val port = conf.getInt("port")

    val routes = new HealthCheckService().route ~
      new PingService().route

    Http().bindAndHandle(routes, "localhost", port)
      .onComplete {
        case Success(msg) => log.info(s"Server started: $msg")
        case Failure(err) =>
          println(s"Failed to start server: ${err.getMessage}")
          system.terminate()
      }
  }
}

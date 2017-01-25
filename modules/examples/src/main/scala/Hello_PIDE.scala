package info.hupel.isabelle.examples.scala

import scala.concurrent._
import scala.concurrent.duration._

import monix.execution.Scheduler.Implicits.global

import info.hupel.isabelle._
import info.hupel.isabelle.api._
import info.hupel.isabelle.setup._

object Hello_PIDE extends App {

  val setup = Setup.default(Version("2016")).right.get // yolo

  val transaction =
    for {
      env <- setup.makeEnvironment
      resources = Resources.dumpIsabelleResources().right.get // yolo
      config = resources.makeConfiguration(Nil, "Protocol")
      sys <- System.create(env, config)
      response <- sys.invoke(Operation.Hello)("world")
      _ = println(response.unsafeGet)
      () <- sys.dispose
    } yield ()

  Await.result(transaction, Duration.Inf)

}

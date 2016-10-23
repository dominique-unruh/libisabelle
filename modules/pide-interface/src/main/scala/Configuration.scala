package info.hupel.isabelle.api

import java.nio.file.{Files, Path}

/** Convenience constructors for [[Configuration configurations]]. */
object Configuration {

  /**
   * Creates a [[Configuration configuration]] from an additional path, that
   * is, the specified session is declared in a `ROOT` file in that path
   * (or indirectly via a `ROOTS` file).
   *
   * In almost all cases, it should refer to a session which has one of the
   * `Protocol` session of the accompanying Isabelle sources
   * as an ancestor, or includes these theories in some other way. To manage
   * these sources automatically, you may use the
   * `[[info.hupel.isabelle.setup.Resources Resources]]` class.
   *
   * The given path must not be identical to or be a subdirectory of the
   * Isabelle home path. It must also, either directly or indirectly via a
   * `ROOTS` file, contain declarations for all ancestor sessions.
   */
  def fromPath(path: Path, session: String) =
    Configuration(List(path), session)

  /**
   * Creates a [[Configuration configuration]] with an empty path, that is,
   * it must be a session included in the Isabelle distribution or registered
   * as a component.
   *
   * Unless using a custom Isabelle distribution, a
   * [[info.hupel.isabelle.System.create system created]] with such a
   * configuration will be unable to reply to any
   * [[info.hupel.isabelle.Operation operation]].
   */
  def fromBuiltin(session: String) =
    Configuration(Nil, session)

}

/**
 * Represents the location and name of a ''session'' (Isabelle terminology).
 *
 * Refer to the Isabelle system manual for details about sessions.
 * `libisabelle` assumes that users are familiar with the session handling
 * of Isabelle.
 *
 * Creation of configurations is completely unchecked. Errors such as
 * non-existing paths will only manifest themselves when attempting to
 * [[info.hupel.isabelle.System.build build]] a configuration or
 * [[info.hupel.isabelle.System.create create]] a
 * [[info.hupel.isabelle.System system]]. Nonetheless, users should go
 * through one of the constructors in the
 * [[Configuration$ companion object]].
 */
final case class Configuration(paths: List[Path], session: String) {
  override def toString: String =
    s"session $session" + (paths match {
      case Nil => ""
      case ps => " at " + ps.mkString(":")
    })
}

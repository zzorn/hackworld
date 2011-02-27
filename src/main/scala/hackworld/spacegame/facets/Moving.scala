package hackworld.spacegame.facets

import org.scalaprops.Property
import com.jme3.math.{Quaternion, Vector3f}
import hackworld.spacegame.entity.Facet

/**
 * Something that moves
 */
class Moving extends Facet {

  val velocity = p('velocity, new Vector3f())
  val rotation = p('rotation, new Quaternion())

  val thrust = p('thrust, new Vector3f())
  val torque = p('torque, new Quaternion())

  override def update(tpf: Float) {
    withFacet[Location]{ l =>
      // Adjust thrust direction based on direction
      val directedThrust = l.dir().mult(thrust())

      velocity := velocity().add(directedThrust.mult(tpf))
      rotation := rotation().add(torque().mult(tpf))

      l.pos := l.pos().add(velocity().mult(tpf))
      l.dir := l.dir().add(rotation().mult(tpf))
      l.pos
    }
  }

}













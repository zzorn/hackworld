package hackworld.spacegame.facets

import com.jme3.math.{Quaternion, Vector3f}
import hackworld.spacegame.entity.Facet

/**
 * Something with thrusters that can be moved about
 */
class Thrustable extends Facet {

  val thrusters = p('thrusters, List[Thruster]() )

  override def update(tpf: Float) {

    val totalThrust = new Vector3f()
    val totalTorque = new Quaternion()

    val controllable: Controllable = facet[ Controllable ]

    thrusters() foreach {t =>
      val activation = controllable.control(t.inputCommand)
      val scale = activation * tpf
      totalThrust.addLocal( t.maxThrust.mult(scale) )
      totalTorque.addLocal( t.maxTorque.mult(scale) )
    }

    facet[Moving].thrust := totalThrust
    facet[Moving].torque := totalTorque
  }

}
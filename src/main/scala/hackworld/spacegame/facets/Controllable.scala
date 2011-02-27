package hackworld.spacegame.facets

import java.lang.String
import com.jme3.input.controls.{ActionListener, AnalogListener, InputListener}
import hackworld.spacegame.entity.Facet

/**
 * 
 */
// TODO: Add listener for listening to actions
class Controllable extends Facet {

  private var _controls: Map[Symbol, Float] = Map()

  def controls: Map[Symbol, Float] = _controls

  def control(controlName: Symbol): Float = _controls.getOrElse(controlName, 0)

  def setControl(controlName: Symbol, value: Float) {
    _controls += (controlName -> value)
  }


}


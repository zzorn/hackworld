package hackworld.spacegame.generators

import com.jme3.math.ColorRGBA
import com.jme3.asset.AssetManager
import hackworld.spacegame.entity.Entity

/**
 * Generates lazor beams.
 */
case class BeamGenerator(length: Float, width: Float, color: ColorRGBA, speed: Float, damage: Float) extends Generator {
  def generate(assetManager: AssetManager): Entity = {
    new Entity()
  }
}
package hackworld.spacegame.generators

import hackworld.spacegame.entity.Entity
import com.jme3.asset.AssetManager

/**
 * 
 */

trait Generator {

  def generate(assetManager: AssetManager): Entity

}
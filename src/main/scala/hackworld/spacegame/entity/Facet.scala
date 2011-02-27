package hackworld.spacegame.entity

import org.scalaprops.Bean
import org.codehaus.jackson.JsonNode

/**
 * Some aspect of an entity.
 */
trait Facet extends Bean {

  private var _entity: Entity = null

  def entity_=(e: Entity) = _entity = e
  def entity: Entity = _entity

  /**
   * Initialize the facet, using specified configuration data.
   */
  def init(configData: JsonNode) {}

  /**
   * Updates the facet if needed.
   * @param tpf the time in seconds since the last call to this method.
   */
  def update(tpf: Float) {}

  protected def getFacet[T <: Facet](implicit m: Manifest[T]): Option[T] = {
    if (entity == null) None
    else entity.getFacet[T](m)
  }

  protected def facet[T <: Facet](implicit m: Manifest[T]): T = {
    if (entity == null) throw new IllegalArgumentException("No facet of type " + m.erasure.getName + " found, because the entity is not specified")
    else entity.facet[T](m)
  }

  protected def withFacet[T <: Facet](op: T => Unit)(implicit m: Manifest[T]) = {
    entity.withFacet[T](op)(m)
  }
}
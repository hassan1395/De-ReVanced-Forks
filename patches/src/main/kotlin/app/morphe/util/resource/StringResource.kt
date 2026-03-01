package app.morphe.util.resource

import org.w3c.dom.Document
import org.w3c.dom.Node

class StringResource(
    name: String,
    val value: String
) : BaseResource(name, "string") {
    override fun serialize(ownerDocument: Document, resourceCallback: (BaseResource) -> Unit) =
        super.serialize(ownerDocument, resourceCallback).apply {
            textContent = value
        }

    override fun toString(): String {
        return "StringResource(value='$value')"
    }

    companion object {
        fun fromNode(node: Node): StringResource {
            val name = node.attributes.getNamedItem("name").textContent
            val value = node.textContent
            return StringResource(name, value)
        }
    }
}

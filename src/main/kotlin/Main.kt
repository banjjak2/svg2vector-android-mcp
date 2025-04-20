import com.vector.svg2vectorandroid.SvgFilesProcessor
import io.ktor.utils.io.streams.*
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.coroutines.*
import kotlinx.io.asSink
import kotlinx.io.buffered
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

fun main() {
    val server = Server(
        serverInfo = Implementation(
            name = "Convert Svg to VectorDrawable",
            version = "1.0.0"
        ),
        options = ServerOptions(capabilities = ServerCapabilities(tools = ServerCapabilities.Tools(listChanged = true)))
    )

    server.addTool(
        name = "convert_svg_to_vector",
        description = "Convert svg to vector",
        inputSchema = Tool.Input(
            properties = buildJsonObject {
                putJsonObject("source") {
                    put("type", "string")
                    put("description", "Absolute Path where the SVG files to be converted are located")
                }
                putJsonObject("destination") {
                    put("type", "string")
                    put("description", "Absolute Path to save the converted files")
                }
            },
            required = listOf("source")
        )
    ) { request ->
        val source = request.arguments["source"]?.jsonPrimitive?.content
        val destination = request.arguments["destination"]?.jsonPrimitive?.content
        if (source == destination) {
            return@addTool CallToolResult(
                content = listOf(TextContent("동일한 위치는 불가능합니다."))
            )
        }
        if (source.isNullOrBlank() || destination.isNullOrBlank()) {
            return@addTool CallToolResult(
                content = listOf(TextContent("위치가 지정되지 않았습니다."))
            )
        }

        withContext(Dispatchers.IO) {
            val svgFileProcessor = SvgFilesProcessor(source, destination, "xml", null)
            svgFileProcessor.process()
        }

        CallToolResult(content = listOf(TextContent("source: $source, destination: $destination")))
    }

    val transport = StdioServerTransport(
        inputStream = System.`in`.asInput(),
        outputStream = System.out.asSink().buffered()
    )
    runBlocking {
        server.connect(transport)
        val done = Job()
        server.onClose { done.complete() }
        done.join()
    }
}
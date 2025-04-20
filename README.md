# svg2vector-android-mcp

## build
```shell
./gradlew shadowJar
```

## installation
cursor mcp.json
```json
{
  "Svg to VectorDrawable": {
    "command": "java",
    "args": [
      "-jar",
      "jar_path/svg2vector-android-mcp-1.0-SNAPSHOT-all.jar"
    ]
  }
}
```

## References
- [Svg2VectorAndroid](https://github.com/ravibhojwani86/Svg2VectorAndroid)
- [MCP Kotlin Sample](https://github.com/modelcontextprotocol/kotlin-sdk/tree/main/samples/weather-stdio-server)
- [MCP Kotlin Server Example](https://modelcontextprotocol.io/quickstart/server#kotlin)
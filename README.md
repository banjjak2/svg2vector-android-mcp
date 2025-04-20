# svg2vector-android-mcp
다수의 svg 파일을 Android VectorDrawable로 변환하기 위한 간단한 MCP 프로젝트

## Example
<img width="519" alt="image" src="https://github.com/user-attachments/assets/1bc93ce4-5844-4d57-b1c6-9a6e84b3f96e" />


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

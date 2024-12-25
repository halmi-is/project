const { defineConfig } = require("@vue/cli-service");
const path = require("path");

module.exports = defineConfig({
  // 기타 설정들 (예: 빌드 최적화, 경로 설정 등)

  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "src"), // '@'를 'src'로 매핑
      },
    },
  },
});

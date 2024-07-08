import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import {resolve} from "path"
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    AutoImport({
      resolvers:[ElementPlusResolver()],
    }),
    Components({
      resolvers:[ElementPlusResolver()],
    }),
  ],
  server:{
    cors:true,
    proxy:{
      "/api":{
        target:'http://localhost:8888/',
        changeOrigin:true,
        rewrite:path=>path.replace(/^\/api/,'')
      },
      "/auth":{
        target:'http://localhost:9002/',
        changeOrigin:true,
        rewrite:path=>path.replace(/^\/auth/,'')
      },
      "/spark":{
        target:'http://localhost:9001/',
        changeOrigin:true,
        rewrite:path=>path.replace(/^\/spark/,'')
      },
    }
  },
  resolve:{
    alias:{
      '@':resolve(__dirname,'./src')
    }
  }
})

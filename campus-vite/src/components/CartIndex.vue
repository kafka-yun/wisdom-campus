<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="24">
          <div class="chat-box">
            <div v-for="(message, index) in messages" :key="index" class="chat-message" :class="{ 'chat-message-client': message.client, 'chat-message-server': !message.client }">
              {{ message.content }}
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-input v-model="newMessage" placeholder="有问题尽管问我" @keyup.enter.native="sendMessage">
            <el-button slot="append" icon="el-icon-arrow-right" @click="sendMessage"></el-button>
          </el-input>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { res } from '../request/public';
export default {
  data() {
    return {
      newMessage: '',
      messages: [
        { content: '你好，有什么可以帮助你的吗？', client: false },
      ],
      T:{
          params:null,
          data:null,
          url:null,
      },
    };
  },
  methods: {
    sendMessage() {
      if (this.newMessage.trim() !== '') {
        this.messages.push({ content: this.newMessage, client: true });
        this.sendIssue(this.newMessage)
        this.newMessage = '';
      }
    },
    sendIssue(msg){
      this.T.params = {
          message:msg
      }
      this.T.url = "/spark/spark/message"
      this.T.data = "get"
      res(this.T).then(res=>{
          console.log(res)
          this.messages.push({content:res.data, client: false})
      }).catch(err=>{
          console.log(err)
      })
    }
  },
};
</script>

<style scoped>
.chat-box {
  height: 800px;
  border: 1px solid #eee;
  margin-bottom: 10px;
  padding: 10px;
  overflow-y: auto;
}
.chat-message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
}
.chat-message-client {
  background-color: #409eff;
  color: white;
}
.chat-message-server {
  background-color: #f2f6fc;
  color: #606266;
}
</style>

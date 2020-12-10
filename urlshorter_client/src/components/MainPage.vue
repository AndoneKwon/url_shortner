<template>
    <div>
        <h1>{{ msg }}</h1>
        <form>
            <input v-model="url" type="text"  name="url" placeholder="Url Please">
        </form>
        <button @click="submit" value="submit">submit</button>
        <div>
            shortUrl is :
            <div id="returnurl" v-if="this.returnUrl!=null&&this.validate">{{returnUrl}}</div>
            <div v-else-if="this.returnUrl!=null&&!this.validate"><a v-bind:href="this.returnUrl">{{returnUrl}}</a></div>
        </div>
    </div>
</template>
<style scoped>
    div#returnurl{
        display: inline;
    }
</style>
<script>
import axios from 'axios';
export default {
  name: 'MainPage',
  props: {
    msg: String
  },
  methods: {
      submit(){
          if(this.url.substring(0,8)!="https://"&&this.url.substring(0,7)!="http://"){
            console.log(this.url.substring(0,7));
            this.returnUrl="Please insert https or http";
            return;
          }
          axios({
              method:"POST",
              url:"http://localhost:8001/urlShorter",
              data:{
                url:this.url
              },
              headers: {
                "authorization":localStorage.getItem('authorization')
              },
          })
          .then((res)=>{
              console.log(res.data);
              this.returnUrl="http://localhost:8001/"+res.data;
          })
          .catch({
              function (error) {
                  console.log(error+"error");
              }
          })
      }
  },
  data() {
      return {
          url:null,
          validate:false,
          returnUrl:null
      }
  },
}
</script>
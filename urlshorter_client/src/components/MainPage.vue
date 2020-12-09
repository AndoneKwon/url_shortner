<template>
    <div>
        <h1>{{ msg }}</h1>
        <form>
            <input v-model="url" type="text"  name="url" placeholder="Url Please">
        </form>
        <button @click="submit" value="submit">submit</button>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'MainPage',
  props: {
    msg: String
  },
  methods: {
      submit(){
          axios({
              method:"POST",
              url:"http://localhost:8001/urlShorter",
              data:{
                url:this.url
              },
              headers: {
                'Content-Type': 'text/html',
                "authorization":localStorage.getItem('authorization')
              },
          })
          .then((res)=>{
              console.log(res);
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
          url:null
      }
  },
}
</script>
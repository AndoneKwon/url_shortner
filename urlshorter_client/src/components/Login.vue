<template>
    <div>
       <div> 
            <form>
                Id : <input type="text"  name="id" id="id" placeholder="Id">
                <br>
                Password : <input type="password"  name="password" id="password" placeholder="Password">
            </form>
            <br>
        <button @click="submit" value="submit">submit</button></div>
    </div>
</template>
<style>
    form{
        display: inline;
    }
</style>

<script>
import axios from "axios"

export default {
  name: 'LoginPage',
  props: {
    msg: String
  },
  methods: {
      submit(){
          axios({
              method:"POST",
              url:"http://localhost:8001/login",
              data:{
                id:this.url,
                password:this.password
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
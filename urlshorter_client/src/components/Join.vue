<template>
    <div>
       <div> 
            <form>
                Id : <input v-model="uid" type="text" id="uid" name="uid" placeholder="Id">
                <br>
                Password : <input v-model="password" type="password" id="password" name="password" placeholder="Password">
                <br>
                confirm Password : <input v-model="cpassword" type="password" name="cpassword" placeholder="Password">
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
    methods:{
        submit(){
            console.log(this.uid);
          axios({
              method:"POST",
              url:"http://localhost:8001/join",
              data:{
                uid:this.uid,
                password:this.password
              },
          })
          .then((res)=>{
              console.log(res.data);
              if(res.data.code==201){
                this.message=res.data.message;
              }else{
                window.location.href="/"
              }
          })
          .catch({
              function (error) {
                  console.log(error+"error");
              }
          })
      }
    }
}
</script>
<template>
    <div>
       <div> 
            <form>
                Id : <input type="text"  name="url" placeholder="Id">
                <br>
                Password : <input type="password"  name="url" placeholder="Password">
                <br>
                confirm Password : <input type="password"  name="url" placeholder="Password">
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
export default {
    
}
</script>
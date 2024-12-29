<template>
  <div id="error">
    <h1>Can't access</h1>
    <p>
      The item you are looking for can not be accessed.
      <router-link to="/">Click here</router-link> to return Home.
    </p>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#error {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
h1 {
  font-size: 20px;
  text-align: center;
  border-bottom: 1px solid #7A9598;
  padding: 10px;
}
</style>
<template>
    <div class="cocktail-card">
        <img src="../../design/espressoMartini.jpg">
        <h2 id="name"> {{ cocktail.name }}</h2>
    </div>
</template>

<script>
export default {
    props: ['cocktail']
}
</script>

<style scoped>
img {
    display: flex;
    height: 200px; 
}
#name {
    display: flex;
    position: relative;
    margin: 0;
    text-align: center;
    padding: 10px;
    color: white;
    font-weight: 400;
    background-color: #778742;
    flex-grow: 1;
    justify-content: center;
    align-items: center;
    z-index: 1;
}
.cocktail-card {
    display: flex;
    flex-direction: column;
    border: 1px solid #ACACAC;
    border-radius: 10px;
    overflow: hidden;
    height: 255px;
}
</style>

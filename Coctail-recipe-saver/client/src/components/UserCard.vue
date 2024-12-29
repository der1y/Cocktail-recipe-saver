<template>
    <div>
        <h1> {{ user.username }}</h1>
    </div>
</template>

<script>
export default {
    props: ['user']
}
</script>
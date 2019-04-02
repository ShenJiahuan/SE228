<template>
    <div>
        <SearchBar id="search-bar"></SearchBar>
        <Recommend></Recommend>
    </div>
</template>

<script>
    import Recommend from "@/components/Recommend";
    import SearchBar from "@/components/SearchBar";
    export default {
        name: "Index",
        components: {
            SearchBar,
            Recommend,
        }
    }
</script>

<style scoped>
    #search-bar {
        display: block;
    }
</style>
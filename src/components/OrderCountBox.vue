<template>
  <el-input-number
    :class="{'order-count-mini': this.size === 'mini', 'order-count-medium': this.size === 'medium'}"
    name="count"
    :size="this.size"
    :value="num"
    @change="this.setNonNull"
    autocomplete="off"
    :min="parseInt(this.min)"
    :max="parseInt(this.max)">
  </el-input-number>
</template>

<script>
  export default {
    name: "OrderCountBox",
    props: [
      "num",
      "setChosen",
      "size",
      "min",
      "max",
    ],
    methods: {
      setNonNull(value) {
        if (value === undefined) {
          this.$emit("update:num", 2);
          this.$nextTick(() => {
            this.$emit("update:num", 1);
          })
        } else {
          this.$emit("update:num", value);
        }
        if (this.setChosen) {
          this.setChosen();
        }
      }
    }
  }
</script>

<style scoped>
  .order-count-mini {
    width: 100px;
  }

  .order-count-medium {
    width: 160px;
  }
</style>

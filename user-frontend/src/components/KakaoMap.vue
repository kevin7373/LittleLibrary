<template>
  <div id="KakaoMap">
    <div ref="map" style="width:100%; height:400px;"></div>
    
  </div>
</template>

<script>
  export default {
    name: "KakaoMap",
    data() {
      return {
      }
    },
    computed: {
      
    },
    methods: {
    },
    mounted() {
      const container = this.$refs.map
      const options = {
        center: new kakao.maps.LatLng(36.357338, 127.296764),
        level: 3
      }
      const map = new kakao.maps.Map(container, options)
      const markerPosition = new kakao.maps.LatLng(36.355378, 127.298364)
      const marker = new kakao.maps.Marker({
        position: markerPosition
      })
      marker.setMap(map)
      requestAnimationFrame(function() { map.relayout() });
    }
  }
</script>

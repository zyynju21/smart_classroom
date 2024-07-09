const countdown = {
  state: {
    // 倒计时实例
    countdownTimeout: null,
    // 倒计时秒数
    timer: 600
  },
  mutations: {
    ClEAR_COUNTDOWN: (state) => {
      state.countdownTimeout = null
    },
    SET_TIMER: (state, time) => {
      state.timer = time
    }
  },
  actions: {
    // 触发倒计时
    startCountdown ({ commit, state }) {
      clearInterval(state.countdownTimeout)
      state.countdownTimeout = setInterval(() => {
       commit('SET_TIMER', state.timer - 1)
      }, 1000)
    },
    // 停止倒计时
    stopCountdown ({ commit, state }) {
      clearInterval(state.countdownTimeout)
      commit('ClEAR_COUNTDOWN')
    },
    // 重新计时
    resetTimer ({ commit }) {
      commit('SET_TIMER', 600)
    }
  }
}

export default countdown

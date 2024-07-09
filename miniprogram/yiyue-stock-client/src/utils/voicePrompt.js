function voicePrompt (text) {
  // new Audio('http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&pdt=311&text=' + text).play()
  new Audio('http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=9&text=' + text).play()
}

export {
  voicePrompt
}

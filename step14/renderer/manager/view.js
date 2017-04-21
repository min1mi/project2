"use strict"

window.$ = window.jQuery = require('jquery')
var managerService = require('electron').remote.getGlobal('managerService')

var fiNo = $('#fi-no'),
    fiEmail = $('#fi-email'),
    fiName = $('#fi-name'),
    fiTel = $('#fi-tel'),
    fiPosi = $('#fi-posi'),
    fiFax = $('#fi-fax'),
    fiPath = $('#fi-path'),
    fiPassword = $('#fi-pwd');

if (location.search == "") {
  // 새 사용자 등록을 처리한다.
  // 상세보기와 관련된 태그는 감춘다.
  $('.bit-view').css('display', 'none')

  // 새 사용자 등록과 관련된 태그는 보이게 한다.
  $('.bit-new').css('display', '')

  // 추가 버튼에 click 이벤트 핸들러(리)스너)를 등록한다.
  $('#add-btn').click(function() {
    managerService.insert(
      {
        name: fiName.val(),
        tel: fiTel.val(),
        email: fiEmail.val(),
        password: fiPassword.val(),
        posi: fiPosi.val(),
        fax: fiFax.val(),
        path: fiPath.val()
      },
      function() {
        alert('추가 성공!')
        location.href='index.html'
      },
      function(error) {
        alert('멤버 추가 오류!')
        throw error
      }) // insert
  }) // click()

} else { // 기존 사용자 정보를 가져온다.
  // 새 사용자 등록과 관련된 태그는 감춘다.
  $('.bit-new').css('display', 'none')

  // URL에서 학생 번호(no)를 추출한다.
  var no = location.search.substring(1).split('=')[1]

  managerService.detail(no,
    function (result) {
      var manager = result
      fiNo.text(manager.mno)
      fiEmail.val(manager.email)
      fiName.val(manager.name)
      fiTel.val(manager.tel)
      fiPosi.val(manager.posi)
      fiFax.val(manager.fax)
      fiPath.val('url')
    },
    function (error) {
      alert('학생 정보 조회 오류!')
      throw error
  }) // detail

  $('#upd-btn').click(function() {
    managerService.update(
      {
        no: no,
        name: fiName.val(),
        tel: fiTel.val(),
        email: fiEmail.val(),
        password: fiPassword.val(),
        posi: fiPosi.val(),
        fax: fiFax.val(),
        path: fiPath.val()
      },
      function() {
        alert('업데이트 성공!')
        location.href='index.html'
      },
      function(error) {
        alert('멤버 업데이트 오류!')
        throw error
      }) // update
  }) // click()

  $('#del-btn').click(function() {
    managerService.delete(no,
      function() {
        alert('삭제 성공!')
        location.href='index.html'
      },
      function(error) {
        alert('학생 삭제 오류!')
        throw error
      }) // delete
  }) // click()
} // else

$('#lst-btn').click(function() {
  location.href = "index.html"
})

"use strict"

window.$ = window.jQuery = require('jquery')
var studentService = require('electron').remote.getGlobal('teacherService')

var fiNo = $('#fi-no'),
    fiEmail = $('#fi-email'),
    fiName = $('#fi-name'),
    fiTel = $('#fi-tel'),
    fiSchoolName = $('#fi-school-name'),
    fiWorking = $('#fi-working');

if (location.search == "") {
  // 새 사용자 등록을 처리한다.
  // 상세보기와 관련된 태그는 감춘다.
  $('.bit-view').css('display', 'none')

  // 새 사용자 등록과 관련된 태그는 보이게 한다.
  $('.bit-new').css('display', '')

  // 추가 버튼에 click 이벤트 핸들러(리스너)를 등록한다.
  $('#add-btn').click(function() {
    studentService.insert(
      {
        name: fiName.val(),
        tel: fiTel.val(),
        email: fiEmail.val(),
        password: '1111',
        working: (fiWorking.prop('checked') ? 'Y' : 'N'),
        school_nm: fiSchoolName.val()
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

  studentService.detail(no,
    function (result) {
      var student = result
      fiNo.text(student.mno)
      fiEmail.val(student.email)
      fiName.val(student.name)
      fiTel.val(student.tel)
      fiSchoolName.val(student.schl_nm)
      fiWorking.attr('checked', (student.work == 'Y' ? true : false))
    },
    function (error) {
      alert('학생 정보 조회 오류!')
      throw error
  }) // detail

  $('#upd-btn').click(function() {
    studentService.update(
      {
        name: fiName.val(),
        tel: fiTel.val(),
        email: fiEmail.val(),
        no: no,
        working: (fiWorking.prop('checked') ? 'Y' : 'N'),
        school_nm: fiSchoolName.val()
      },
      function() {
        alert('업데이트 성공!')
      },
      function(error) {
        alert('멤버 업데이트 오류!')
        throw error
      }) // update
  }) // click()

  $('#del-btn').click(function() {
    studentService.delete(no,
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

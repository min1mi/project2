"use strict"

window.$ = window.jQuery = require('jquery')
var lectureService = require('electron').remote.getGlobal('lectureService')

var fiNo = $('#fi-no'),
    fiTitl = $('#fi-titl'),
    fiDscp = $('#fi-dscp'),
    fiSdt = $('#fi-sdt'),
    fiEdt = $('#fi-edt'),
    fiQty = $('#fi-qty'),
    fiPric = $('#fi-pric'),
    fiThrs = $('#fi-thrs'),
    fiCrmno = $('#fi-crmno'),
    fiMrno = $('#fi-mrno');

if (location.search == "") {
  // 새 사용자 등록을 처리한다.
  // 상세보기와 관련된 태그는 감춘다.
  $('.bit-view').css('display', 'none')

  // 새 사용자 등록과 관련된 태그는 보이게 한다.
  $('.bit-new').css('display', '')

  // 추가 버튼에 click 이벤트 핸들러(리스너)를 등록한다.
  $('#add-btn').click(function() {
    lectureService.insert(
      {
        no: fiNo.val(),
        titl: fiTitl.val(),
        dscp: fiDscp.val(),
        sdt: fiSdt.val(),
        edt: fiEdt.val(),
        qty: fiQty.val(),
        pric: fiPric.val(),
        thrs: fiThrs.val(),
        crmno: fiCrmno.val(),
        mrno: fiMrno.val()
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

  lectureService.detail(no,
    function (result) {
      var lecture = result
      fiNo.val(lecture.lno),
      fiTitl.val(lecture.titl),
      fiDscp.val(lecture.dscp),
      fiSdt.val(lecture.sdt),
      fiEdt.val(lecture.edt),
      fiQty.val(lecture.qty),
      fiPric.val(lecture.pric),
      fiThrs.val(lecture.thrs),
      fiCrmno.val(lecture.crmno),
      fiMrno.val(lecture.mrno)
    },
    function (error) {
      alert('학생 정보 조회 오류!')
      throw error
  }) // detail

  $('#upd-btn').click(function() {
    lectureService.update(
      {
        no: no,
        titl: fiTitl.val(),
        dscp: fiDscp.val(),
        sdt: fiSdt.val(),
        edt: fiEdt.val(),
        qty: fiQty.val(),
        pric: fiPric.val(),
        thrs: fiThrs.val(),
        crmno: fiCrmno.val(),
        mrno: fiMrno.val()
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
    lectureService.delete(no,
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

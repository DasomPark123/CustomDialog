안드로이드 커스텀 다이얼로그

** 다이얼로그 생성
  - DialogActivity.kt 에서 CustomDialog.Builder로 dialog를 생성한다.

** CustomDialog.Builder.class 메소드 정리
  - setTitle : dialog 타이틀 set
  - setMessage : dialog 메세지 set
  - setTitleColor : dialog 타이틀 색상 set
  - setView : dialog 의 타이틀과 버튼 사이 원하는 뷰 set
  - setPositiveButton : positive 버튼에 text 와 listener 를 set
  - setNegativeButton : negative 버튼에 text 와 listener 를 set
  - (listener 가 null 일 경우 button 은 보이지 않는다.)
  - isClose : x 버튼의 유뮤
  - isCancelable : dialog 의 바깥쪽 터치시 dialog dismiss 여부
  - setBackgroundColor : dialog 배경색 set
  - setAniMode : dialog 에 animation set
    (TYPE_BOTTOM_TO_TOP : 아래에서 위로 올라오는 animation,
    TYPE_TOP_TO_BOTTOM : 위에서 아래로 내려오는 animation,
    TYPE_CENTER : no animation)
  - create : CustomDialog 생성
  - show : dialog show()

** CustomDialog Style
  - Title Style
    CustomDialogWindowTitleStyle 에 item 정의
    (textSize, textColor, typeface)

  - Button Style
    CustomDialogButtonStylePositive, CustomDialogButtonStyleNegative 에 item 정의
    (textSize, textColor,background)

** setDim
    DialogActivity.kt 에서 첫번째 인자값에 원하는 dialog 를 넣어주고 두번째 인자값에 원하는 만큼의 dim 값을 넣어준다.(0.0f - 1.0f)

** setSize
    DialogActivity.kt 에서 첫번째 인자값에 원하는 dialog 를 넣어주고 두번째에 width 세번째에 height 값을 넣어준다.(100이  1cm 이다.)




# GitHubSample


안드로이드에서 많이 언급되고 사용되는 아키텍처, 기술, 기능들을 적용해보고 고민해보기위한 목적으로 시작한 프로젝트입니다.


* 기본적으로 GitHub App의 UI를 따라 만듭니다. [GitHub App_플레이스토어](https://play.google.com/store/apps/details?id=com.github.android&hl=ko)
* 필요한 데이터는 GitHub API를 활용합니다.
    * [API 문서](https://docs.github.com/en/rest) 
    * [서버없이 사용자 인증](https://docs.github.com/en/apps/creating-github-apps/authenticating-with-a-github-app/generating-a-user-access-token-for-a-github-app#using-the-device-flow-to-generate-a-user-access-token)


### 사용중인 것들
- Clean Architecture, MVVM
- Coroutine, flow, livedata
- xml, databinding, compose
- hilt
- navigation, datastore
- retrofit2, kotlin serialization, glide


### 할 것들
* Navigation 활용
    * 앱 진입, 라우팅, 백스택을 관리하는 방법 고민
    * compose와 함께 사용하는 상황 고려

* Compose 활용
    * xml에서 compose로 점진적 변환
    * Design Sytem을 위해 필요한 부분 고민
   
* 아키텍처, 구조, 스타일 고민
    * State로 표현하고 적용하는 방법 고민
    * 멀티모듈 고려

* 많이 쓰이는 기능들 함수 & 모듈화
    * Dialog, BottomSheet, Toast, SnackBar 등
    * 권한, 위치, 미디어, 카메라 등의 안드로이드 기능들
    * String, Spannable 같은 일반적인 것들

* 버전 관리
   * 버전 업데이트
   * Version Catalog 적용
   * gradle 언어 kotlin으로 전환

[이슈로그](https://github.com/lotus0602/GitHubSample/blob/doc/doc/issue_log.md)

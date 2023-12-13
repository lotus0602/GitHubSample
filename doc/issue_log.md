# 이슈로그
개발을 진행하면서 고민되는 부분을 정리합니다


* UI State 설계 스타일
  * 참고

    https://medium.com/@laco2951/android-ui-state-modeling-%EC%96%B4%EB%96%A4%EA%B2%8C-%EC%A2%8B%EC%9D%84%EA%B9%8C-7b6232543f25



* GitHub API 응답모델 설계와 공통처리
  * 여러 API의 실패응답은 동일하지만 동일한 API 에서 성공, 실패 모델이 다름
    ``` kotlin
    // 성공
    {
      deviceCode: "",
      expiresIn: 0,
      interval: 0,
      userCode: "",
      verificationUri: ""
    }
    // 실패
    {
      error: "",
      errorDescription: "",
      errorUri: ""
    }
    ```
  * 현재 처리
  
    각 API의 성공모델이 실패응답을 상속
    ``` kotlin
    data class DeviceCode(
      ...
    ) : FailResponse()
    ```
    
  * 대안
    * Interceptor
    * CallAdapter
    
  * 참고

    https://getstream.io/blog/modeling-retrofit-responses/

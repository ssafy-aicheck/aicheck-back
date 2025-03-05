# Router

# Page Router

Next.js의 Page Router는 파일 시스템 기반 라우팅 방식을 사용한다. 폴더와 파일 이름을 기반으로 애플리케이션의 경로가 결정된다.

## 기본 라우팅 원리

- `pages` 디렉토리의 파일 구조가 URL 경로와 직접 매핑된다.
- 예: `pages/about.js` 파일은 `/about` URL에 매핑된다.

## 동적 라우팅

특정 패턴의 경로를 하나의 페이지로 처리할 수 있다:

- 대괄호(`[]`)를 사용하여 동적 경로 세그먼트를 정의한다.
- 예를 들어, `search/1`로 이동하려면 `pages/search/[id].js` 파일을 생성한다.

## 동적 라우팅 파라미터 접근 방법

동적 URL 파라미터에 접근하는 여러 방법이 있다:

```jsx
// pages/search/[id].js
import { useRouter } from 'next/router';

function SearchPage() {
  const router = useRouter();
  const { id } = router.query;

  return <div>Search ID: {id}</div>;
}

export default SearchPage;

```

## 중첩 동적 라우팅

더 복잡한 경로도 처리할 수 있다:

- `pages/products/[category]/[item].js` 형태로 중첩 동적 라우팅을 구현할 수 있다.
- 이 경우 `/products/electronics/laptop`과 같은 URL에 매핑된다.

## 선택적 캐치올 라우트

- `pages/[[...slug]].js`처럼 선택적 캐치올 라우트를 구현할 수 있다.
- 이렇게 하면 `/`, `/about`, `/products` 등 다양한 경로를 하나의 페이지에서 처리할 수 있다.

# Prefetch

Next.js는 성능 최적화를 위해 기본적으로 페이지 프리페칭 기능을 제공한다.

## 프리페칭이란?

프리페칭은 현재 페이지에서 연결될 가능성이 있는 페이지의 JavaScript 번들을 미리 로드하는 기술이다. 이를 통해 사용자가 링크를 클릭했을 때 페이지 로딩 시간을 크게 단축할 수 있다.

## 프리페칭이 필요한 이유

Next.js는 성능 최적화를 위해 현재 필요한 페이지의 JavaScript 번들만 로드한다. 프리페칭 없이는 사용자가 새 페이지로 이동할 때 해당 페이지의 번들을 그때서야 다운로드하게 되어 로딩 지연이 발생한다.

## 프리페칭 작동 방식

- Next.js는 `<Link>` 컴포넌트가 뷰포트에 나타나면 자동으로 해당 링크의 페이지를 프리페치한다.
- 프로덕션 빌드에서만 작동하며, 개발 모드에서는 프리페칭이 적용되지 않는다.

## 확인 방법

- 개발 모드(`npm run dev`)에서는 프리페치 동작을 확인할 수 없다.
- 프로덕션 빌드 후 실행해야 프리페치 동작을 확인할 수 있다:
    
    ```bash
    npm run buildnpm run start
    
    ```
    
- 브라우저 개발자 도구의 네트워크 탭에서 프리페치된 JavaScript 파일을 확인할 수 있다.

## 자동 vs 수동 프리페칭

- `<Link>` 컴포넌트는 자동으로 프리페칭을 처리한다:
    
    ```jsx
    import Link from 'next/link';
    
    function NavLinks() {
      return (
        <nav>
          <Link href="/about">About</Link>
          <Link href="/products">Products</Link>
        </nav>
      );
    }
    ```
    
- `router.push()`와 같은 JavaScript 함수로 라우팅을 구현한 경우에는 프리페칭이 자동으로 이루어지지 않는다. 이런 경우 `router.prefetch()`를 사용해 수동으로 프리페칭을 구현해야 한다:
    
    ```jsx
    import { useRouter } from 'next/router';
    import { useEffect } from 'react';
    
    function MyComponent() {
      const router = useRouter();
    
      useEffect(() => {
        router.prefetch('/test');
      }, []);
    
      return (
        <button onClick={() => router.push('/test')}>
          Go to Test Page
        </button>
      );
    }
    ```
    

## 프리페칭 비활성화

필요한 경우 `<Link>` 컴포넌트의 프리페칭을 비활성화할 수 있다:

```jsx
<Link href="/about" prefetch={false}>About</Link>
```

## 프리페칭 제한 사항

- 프리페칭은 프로덕션 환경에서만 작동한다.
- 사용자가 느린 연결이나 데이터 절약 모드를 사용하는 경우 브라우저가 프리페칭을 제한할 수 있다.
- 모바일 장치에서는 배터리 절약 등의 이유로 프리페칭이 제한될 수 있다.

# API Route

Next.js의 API Route는 서버리스 함수를 쉽게 만들 수 있는 기능이다.

## 기본 사용법

- `pages/api` 폴더에 파일을 만들어 API 엔드포인트를 정의한다.
- 예: `pages/api/hello.js` 파일은 `/api/hello` URL에 매핑된다.

```jsx
// pages/api/hello.js
export default function handler(req, res) {
  res.status(200).json({ name: 'John Doe' });
}
```

## HTTP 메서드 처리

```jsx
jsx
Copy
export default function handler(req, res) {
  const { method } = req;

  switch (method) {
    case 'GET': res.status(200).json({ users: ['John', 'Jane'] }); break;
    case 'POST': res.status(201).json({ message: '사용자 생성 완료' }); break;
    default: res.status(405).end(`Method ${method} Not Allowed`);
  }
}
```

## 동적 API 라우트

- `pages/api/users/[id].js` 형식으로 동적 라우트를 만들 수 있다.
- URL 파라미터는 `req.query`로 접근한다.

## API Route의 장점

- 별도의 백엔드 서버가 필요 없다.
- API 키 같은 민감한 정보를 안전하게 사용할 수 있다.
- 프론트엔드와 백엔드를 한 프로젝트에서 관리할 수 있다.

---

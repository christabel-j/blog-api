# Frontend Learning Notes

A running reference of every concept, explanation, and decision from our pairing sessions.
Updated at the end of each step.

---

## The Big Picture

Your Spring Boot API is the **backend**: it handles HTTP requests, talks to the database, and returns JSON.

Your React app is the **frontend**: it runs entirely in the browser, calls your API to fetch JSON, and turns that JSON into HTML the user can see and interact with.

The key shift from traditional server-rendered apps (e.g. Thymeleaf):
- **Before:** server builds the HTML → sends it to the browser
- **Now (SPA):** server sends raw JSON → React builds the HTML in the browser

This pattern is called a **Single Page Application (SPA)** — there is one HTML file, and React dynamically swaps out what's displayed inside it without doing a full page reload.

---

## Your API at a Glance

Base URL: `http://localhost:8080/api/v1/posts`

| Method | Path              | What it does            | Returns         |
|--------|-------------------|-------------------------|-----------------|
| GET    | `/posts`          | Get all posts           | `PostResponse[]`|
| GET    | `/posts/{id}`     | Get one post by id      | `PostResponse`  |
| POST   | `/posts`          | Create a new post       | `PostResponse` (201) |
| PUT    | `/posts/{id}`     | Update an existing post | `PostResponse`  |
| DELETE | `/posts/{id}`     | Delete a post           | 204 No Content  |

`PostResponse` shape:
```json
{
  "id": "uuid",
  "title": "string",
  "content": "string",
  "created": "instant",
  "updated": "instant"
}
```

---

## Step 1 — Creating the React Project

### New Concept: npm

**npm** (Node Package Manager) is the dependency manager for JavaScript.

| Java world        | JavaScript world  |
|-------------------|-------------------|
| Maven             | npm               |
| `pom.xml`         | `package.json`    |
| `mvn install`     | `npm install`     |
| `~/.m2/`          | `node_modules/`   |

### New Concept: Vite

**Vite** (pronounced "veet") is the build tool for our React project. It does two jobs:

1. **Dev server** — runs your app locally at `localhost:5173` with hot reload (changes appear in the browser instantly without a full refresh)
2. **Bundler** — compiles and optimises all your JavaScript files into browser-ready output for production

The older alternative you'll see online is **Create React App (CRA)** — avoid it. It's slow and effectively abandoned. Vite is the modern standard.

### The command we ran

```bash
npm create vite@latest . -- --template react
```

- `npm create vite@latest` — runs Vite's project generator (like opening Spring Initializr)
- `.` — create the project in the current directory (we were inside `frontend/`)
- `-- --template react` — plain JavaScript React (not TypeScript, not Vue, not Svelte)

We chose **plain JavaScript** (not TypeScript) to keep things simple while learning. TypeScript adds safety through type annotations, but the extra syntax would get in the way right now.

Then we installed dependencies:

```bash
npm install
```

This downloaded 24 packages into `node_modules/` — you never commit this folder to git.

### Project Structure

```
frontend/
├── index.html          ← The single HTML file the whole app lives in
├── vite.config.js      ← Vite configuration (we'll edit this later for API proxying)
├── package.json        ← Dependency list + npm scripts (your pom.xml equivalent)
├── package-lock.json   ← Resolved dependency tree (auto-managed, don't edit)
├── public/             ← Static assets served as-is (favicon, etc.)
└── src/                ← All your code lives here (like src/main/java/)
    ├── main.jsx        ← Entry point — starts the whole app (like BlogApplication.java)
    ├── App.jsx         ← Root component — the top of your UI tree
    ├── App.css         ← Styles for App
    └── index.css       ← Global styles
```

**Files that matter right now:** `main.jsx` and `App.jsx`. Everything else is plumbing.

### Starting the dev server

```bash
npm run dev
```

Opens at `http://localhost:5173`. Changes you make to files in `src/` appear in the browser instantly — no restart needed.

---

---

## Step 2 — Understanding the Project Structure

### How the browser knows to run anything: `main.jsx` → `index.html`

`index.html` contains one empty div:
```html
<div id="root"></div>
```

`main.jsx` finds it and hands it to React:
```js
createRoot(document.getElementById('root')).render(<App />)
```

React injects the entire UI into that div. That's how a blank HTML file becomes a full application — React owns that div and renders everything inside it dynamically.

`StrictMode` (also in `main.jsx`) is a development-only helper that runs your code twice to catch bugs early. It has no effect on what the user sees in production.

---

### New Concept: Component

A **component** is a JavaScript function that returns UI. React applications are built by composing components together — like Lego bricks.

Spring Boot analogy: if a `@Service` is reusable business logic, a component is reusable UI.

Rules:
1. The function name **must start with a capital letter** — that's how React distinguishes components from regular HTML tags (`<App />` vs `<div>`)
2. It **must return UI**

```jsx
function App() {
  return (
    <h1>Hello</h1>   // this is the UI it returns
  )
}

export default App   // makes it importable in other files
```

`export default` / `import` works exactly like `public` class visibility in Java — if it's not exported, it's private to that file. `main.jsx` does `import App from './App.jsx'` to pull it in.

---

### New Concept: JSX

**JSX** is a syntax extension that lets you write HTML-like markup directly inside JavaScript. Vite compiles it to regular JavaScript behind the scenes.

Two important differences from real HTML:

**1. `className` instead of `class`**
```jsx
<div className="hero">   // not class="hero"
```
Because `class` is a reserved keyword in JavaScript (as in Java).

**2. Curly braces `{}` = "run JavaScript here"**
```jsx
<p>Count is {count}</p>
```
`{}` drops out of markup mode into JavaScript mode and inserts the value. You'll use this constantly.

---

### New Concept: State (`useState`)

**State** is data that, when it changes, causes React to automatically re-render (update) the component. It's the reactive part of React.

```js
const [count, setCount] = useState(0)
```

- `useState(0)` — creates a piece of state with initial value `0`
- `count` — the current value (read-only)
- `setCount` — the only way to change the value

You **never** do `count = count + 1` directly. You always call `setCount(newValue)`. That's what tells React a change happened and triggers the re-render.

The `[count, setCount]` syntax is JavaScript array destructuring — same idea as unpacking in other languages.

---

### New Concept: Hook (`useState` is a hook)

A **hook** is a special React function you call inside a component to "hook into" React features. `useState` is the most fundamental one. We'll meet more hooks as we build (e.g. `useEffect` for API calls).

Rules for hooks:
- Always called at the **top level** of a component function (not inside loops or if-statements)
- Always start with the word `use` — that's how you spot them

---

### The counter button — putting it all together

```jsx
<button onClick={() => setCount((count) => count + 1)}>
  Count is {count}
</button>
```

1. User clicks the button
2. `onClick` fires → calls `setCount` with `count + 1`
3. React re-renders `App` with the new value
4. `{count}` in the JSX now shows the updated number

No page refresh. No server round-trip. React handles it all in the browser.

---

---

## Step 3 — Display a Simple Page

### What we did

Replaced all of the Vite boilerplate in `App.jsx` with our own minimal content — a heading and a paragraph. The goal: prove you can write a component from scratch and see it render.

### Clean `App.jsx`

```jsx
function App() {
  return (
    <>
      <h1>My Blog!</h1>
      <p>The start of my blog...</p>
    </>
  )
}

export default App
```

### New Concept: Fragment (`<>...</>`)

React requires every component to return **one root element**. If you have two siblings (`<h1>` and `<p>`), you need something to wrap them.

A **Fragment** (`<>...</>`) satisfies that rule without adding any real HTML to the DOM. If you inspect the page in browser devtools, you won't see a wrapper div — it's invisible to the browser.

The alternative is a real `<div>`, but that adds unnecessary nesting to the DOM. Use a Fragment when you just need to satisfy React's one-root rule.

### Unused imports

JavaScript doesn't error on unused imports (unlike Java's stricter compiler warnings). But unused imports are dead weight — they tell readers "this file uses these things" when it doesn't. Only import what you actually use.

---

---

## Step 4 — Fetch and Display Blog Posts

### The CORS Problem (simplified)

Your React app runs on port **5173**. Your Spring Boot API runs on port **8080**.

The browser has a security rule: JavaScript on one origin (host + port) is not allowed to make requests to a different origin without permission. Different ports = different origins. So `localhost:5173` calling `localhost:8080` gets **blocked by the browser** — even though it's all on your own machine.

This is called **CORS** (Cross-Origin Resource Sharing).

**The fix we used: Vite Proxy**

Instead of calling `http://localhost:8080/api/v1/posts` directly, the frontend calls just `/api/v1/posts`. Vite intercepts that request and secretly forwards it to `http://localhost:8080` on our behalf. The browser never sees a cross-origin request, so it never blocks it.

Config added to `vite.config.js`:
```js
server: {
  proxy: {
    '/api': 'http://localhost:8080'
  }
}
```

This only works in development. In production you'd solve CORS differently (e.g. putting both apps behind the same domain/port with a reverse proxy like Nginx, or adding `@CrossOrigin` to the Spring Boot controller).

---

### New Concept: `useEffect`

`useEffect` is React's way of saying: *"after this component renders, run this code."*

It's for **side effects** — things that reach outside the component, like calling an API, setting a timer, or reading from localStorage.

Spring Boot analogy: like `@PostConstruct` — code that runs automatically after the bean is initialised.

```js
useEffect(() => {
  // code that runs after the component renders
}, [])
```

The second argument is the **dependency array** — it controls when the effect re-runs:

| Dependency array | When the effect runs |
|---|---|
| `[]` | Once, when the component first appears on screen |
| `[someValue]` | Every time `someValue` changes |
| nothing | After every single render (almost never what you want) |

---

### How the API call works — full flow

```js
useEffect(() => {
  fetch('/api/v1/posts')
    .then(response => response.json())
    .then(data => setPosts(data))
}, [])
```

- **`fetch('/api/v1/posts')`** — browser's built-in HTTP client. No import needed. Vite's proxy forwards this to `http://localhost:8080/api/v1/posts`.
- **`.then(response => response.json())`** — parses the JSON body from the HTTP response
- **`.then(data => setPosts(data))`** — puts the array of posts into state, which triggers a re-render

`.then()` is **promise chaining** — JavaScript's way of handling async results (like `CompletableFuture` in Java).

**The real render sequence (important):**
1. Component renders immediately with `posts = []` (empty list — initial state)
2. `useEffect` fires *after* that first render and makes the API call
3. API responds → `setPosts(data)` is called
4. React detects state changed → component **re-renders** with the real posts
5. Posts appear on screen

There is always a brief moment where the list is empty. Later we add a loading state to handle this gracefully.

---

### Displaying a list in JSX

```jsx
{posts.map(post => (
  <div key={post.id}>
    <h2>{post.title}</h2>
    <p>{post.content}</p>
  </div>
))}
```

- **`.map()`** — JavaScript array method that transforms each item into something else. Here: each post object → a JSX `<div>`
- **`key={post.id}`** — required by React on every list item. React uses it internally to track which item is which when the list changes. Always use a stable unique id (your UUID from Spring Boot is perfect).

---

---

## Step 5 — Create a Post

### New Concept: Controlled Inputs

In plain HTML, a form field holds its own value inside the DOM — you read it out when you need it. In React, the value lives in **state**, and the input always reflects that state. This is called a **controlled input**.

```jsx
const [title, setTitle] = useState('')

<input
  value={title}
  onChange={e => setTitle(e.target.value)}
/>
```

- `value={title}` — input always displays whatever is in state
- `onChange` — fires on every keystroke; `e.target.value` is the current content of the box; we immediately push it into state

Spring Boot analogy: binding a form field to a field on a `@RequestBody` DTO — the field and the object stay in sync.

### Sending a POST request

```js
fetch('/api/v1/posts', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ title, content })
})
```

- `method: 'POST'` — the HTTP verb
- `headers` — tells Spring Boot the body is JSON
- `body: JSON.stringify({ title, content })` — converts JavaScript object to a JSON string. `{ title, content }` is shorthand for `{ title: title, content: content }`

### Updating state after creation

```js
.then(newPost => {
  setPosts([...posts, newPost])
  setTitle('')
  setContent('')
})
```

- `[...posts, newPost]` — the **spread operator** (`...`) creates a new array with all existing posts plus the new one. You never mutate state directly in React — always create a new value.
- Clearing `title` and `content` state empties the form fields automatically (because they're controlled inputs).

### Preventing default form behaviour

```js
function handleSubmit(e) {
  e.preventDefault()  // stops the browser reloading the page on form submit
```

Always call `e.preventDefault()` on form submissions in React, otherwise the browser reloads the page.

---

---

## Step 6 — Update a Post

### New Concept: Props

**Props** (short for *properties*) are how you pass data from a parent component into a child component. Like method parameters in Java.

```jsx
// Parent passes data down:
<PostCard post={post} />

// Child receives and uses it:
function PostCard({ post }) {
  return <h2>{post.title}</h2>
}
```

The `{ post }` in the function signature is JavaScript destructuring — unpacking the props object. You'll see this everywhere in React.

### New Concept: Callback Props (events flowing up)

Data flows **down** via props. But a child can't directly change the parent's state. Instead, the parent passes a **function** down as a prop, and the child calls it when something happens. This is how events travel upward.

```jsx
// Parent defines the behaviour:
function handleUpdate(updatedPost) {
  setPosts(posts.map(p => p.id === updatedPost.id ? updatedPost : p))
}

// Parent passes the function down as a prop:
<PostCard post={post} onUpdate={handleUpdate} />

// Child calls it after a successful save:
onUpdate(updatedPost)
```

**The golden rule: data flows down via props, events flow up via callback functions.**

### New Concept: Multiple Components

We extracted `PostCard` into its own file (`PostCard.jsx`) so that each post manages its own editing state independently. If edit state lived in `App`, one post being edited would affect the whole list.

Each component should be responsible for **one thing**:
- `App` — owns the list of posts, the create form, and coordinates updates
- `PostCard` — owns the display and editing of a single post

### Conditional Rendering

```jsx
if (isEditing) {
  return <div>...edit form...</div>
}

return <div>...normal view...</div>
```

A component can return different JSX based on state. React renders whichever branch is reached — this is called **conditional rendering**.

### Replacing one item in a list

```js
setPosts(posts.map(p => p.id === updatedPost.id ? updatedPost : p))
```

`map` over every post — if the id matches, swap in the updated version; otherwise keep the original. This is the standard React pattern for updating one item in an array without mutating state.

### Template literals in JavaScript

```js
`/api/v1/posts/${post.id}`
```

JavaScript's string interpolation. Backticks + `${}` = insert a variable. Equivalent to `String.format("/api/v1/posts/%s", post.id)` in Java.

---

---

## Step 7 — Delete a Post

The same callback prop pattern as update — the Delete button lives in `PostCard`, and `App` is told about it via an `onDelete` prop.

### Handling a 204 No Content response

`DELETE /api/v1/posts/{id}` returns no body. Never call `.json()` on an empty response — it will throw an error. Instead:

```js
fetch(`/api/v1/posts/${post.id}`, { method: 'DELETE' })
  .then(() => onDelete(post.id))
```

`.then(() => ...)` fires when the request completes successfully but ignores the empty body entirely.

### Removing one item from a list

```js
function handleDelete(id) {
  setPosts(posts.filter(p => p.id !== id))
}
```

`filter` returns a new array containing only the items where the condition is true — here, every post whose id does *not* match. Same rule as always: never mutate state directly, always return a new array.

| Operation | Array method | Pattern |
|---|---|---|
| Add an item | spread | `[...posts, newPost]` |
| Replace one item | `map` | `posts.map(p => p.id === id ? updated : p)` |
| Remove one item | `filter` | `posts.filter(p => p.id !== id)` |

---

### Key concept: Functions are values in JavaScript

In JavaScript, functions are **first-class values** — you can store them in variables and pass them around just like strings or numbers. This is what makes callback props work.

```js
// App defines the function:
function handleDelete(id) {
  setPosts(posts.filter(p => p.id !== id))
}

// App passes it as a prop — no () because we're passing the function itself, not calling it:
<PostCard onDelete={handleDelete} />

// PostCard receives it under the name onDelete and calls it when ready:
.then(() => onDelete(post.id))  // NOW we call it, with ()
```

`onDelete` inside `PostCard` is just a local name pointing to `handleDelete` from `App`. Calling `onDelete(post.id)` runs the code in `App`.

Java equivalent: passing a `Consumer<UUID>` functional interface as a method parameter — same idea, lighter syntax in JavaScript because functions are natively first-class with no interface needed.

---

---

## Step 8 — Improve the UI with Tailwind CSS

### What is Tailwind CSS?

Tailwind is a **utility-first CSS framework** — instead of writing CSS in separate files, you apply small single-purpose classes directly on elements in JSX.

```jsx
// Without Tailwind — separate CSS file needed:
<button className="my-button">Click</button>

// With Tailwind — styles live right on the element:
<button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
  Click
</button>
```

Each class does exactly one thing. You compose them like Lego. No naming things, no switching between files.

### Setup (Tailwind v4 with Vite)

1. Install: `npm install tailwindcss @tailwindcss/vite`
2. Add the plugin to `vite.config.js`:
```js
import tailwindcss from '@tailwindcss/vite'
export default defineConfig({
  plugins: [react(), tailwindcss()],
})
```
3. Replace `index.css` content with just: `@import "tailwindcss";`
4. Restart the dev server — config changes always need a restart

### Common Tailwind class patterns

| Class | What it does |
|---|---|
| `bg-gray-100` | background colour (gray scale 100–900) |
| `text-white` | text colour |
| `text-sm / text-xl` | font size |
| `font-semibold / font-bold` | font weight |
| `px-4 py-2` | padding: x-axis (left+right) and y-axis (top+bottom) |
| `mb-4` | margin-bottom |
| `rounded-lg / rounded-xl` | border-radius |
| `shadow` | drop shadow |
| `flex flex-col gap-4` | flexbox column layout with gaps |
| `max-w-2xl mx-auto` | cap width and centre horizontally |
| `min-h-screen` | minimum height = full viewport |
| `w-full` | width 100% |
| `self-end` | align just this item to the end of the flex container |
| `resize-none` | prevent textarea manual resizing |

### Modifiers — applying styles conditionally

Prefix any class with a condition using `:` to apply it only in that state:

| Modifier | When it applies |
|---|---|
| `hover:bg-blue-700` | when the mouse is over the element |
| `focus:ring-2` | when the element is focused (keyboard/click) |
| `transition-colors` | smoothly animate colour changes (pairs with hover:) |

---

<!-- Step 9 and beyond will be added here as we go -->

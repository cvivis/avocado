
// import logo from './logo.svg';
// import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Detail from './component/normal/board/Detail';
import BoardList from './component/normal/board/BoardList';
import CategoryList from './component/normal/board/CategoryList';

// import NormalBid from './component/normal/auction/normalBid';
import Header from './common/Header';
import Footer from './common/Footer';
import Login from './component/member/Login';
import Signup from './component/member/Signup';
import MyPage from './component/member/MyPage';
import NormalBidPage from './component/normal/auctionPage/NormalBidPage';
// // import NormalBid from './component/normal/auction/normalBid';
// import Header from './common/Header';
// import Footer from './common/Footer';

function App() {
  // const nowPrice = 10000;
  // const nowBidName = "홍길동";

  return (

    <Router>
      <div>
        <header>
          <Header></Header>
        </header>

      </div>
      <div className="App">

        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/normal/list" element={<BoardList />}></Route>
          <Route path="/normal/detail/:id" element={<Detail />}></Route>
          <Route path="/normal/list/sort-category" element={<CategoryList />}></Route>
          <Route path="/member/login" element={<Login />}></Route>
          <Route path="/member/signup" element={<Signup />}></Route>
          <Route path="/member/myPage" element={<MyPage />}></Route>
          <Route path="/normal/auctionPage/NormalBidPage" element={<NormalBidPage />}></Route>

        </Routes>
      </div>
      <footer>
        <Footer></Footer>
      </footer>
    </Router>
  );
}

export default App;
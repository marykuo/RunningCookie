# RunningCookie
A practice of coding Java game in class.

## 執行畫面
<!--running-->
<img src="https://i.imgur.com/RVWossG.jpg" width="700"/>

在上圖中可以看到：
* 左上方有**生命值**，在觸碰到障礙物時會扣生命值，每種障礙物會扣的生命值多寡不同，扣至生命值歸零時遊戲結束。
* 右上方有**分數**，每次吃到蛋糕時，會加10分，遊戲結束後可將自己的名字和分數加入排行榜中。

## 動作介紹
### 跳躍
* 按一下空白鍵，角色可進行「一段跳」。
* 按兩下空白鍵，角色可進行「二段跳」(如下圖)。
<!--2nd jump-->
<img src="https://i.imgur.com/vOGt4TP.png" width="700"/>

### 滑行
* 按方向鍵下，角色可進行「滑行」(如下圖)。
<!--slide-->
<img src="https://i.imgur.com/Uj7hxkX.png" width="700"/>

## 地圖介紹
<!--floor-->
<img src="https://i.imgur.com/0Ed1IXF.png" width="700"/>

* 地圖中會出現一格空白(如上圖)，進行一段跳即可順利通過，若掉入空白則遊戲結束(如下圖)。

<!--gameover-->
<img src="https://i.imgur.com/k0CpJjT.png" width="700"/>

## 障礙物
障礙物分為三種
### 一格高：進行一段跳即可順利通過。
<!--obstacle1--><img src="https://i.imgur.com/2gsiVnU.png" height="200"/>

### 兩格高：進行二段跳即可順利通過。
<!--obstacle3-->
<img src="https://i.imgur.com/jOULGVE.png" height="200"/>

### 由上而下：進行滑行即可順利通過。
<!--obstacle4-->
<img src="https://i.imgur.com/0roQvS5.png" height="500"/>

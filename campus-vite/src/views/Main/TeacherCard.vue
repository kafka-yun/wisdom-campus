<template>
<div class="card">
		<div class="content">
			<div class="imgBx">
			  <img src="https://th.bing.com/th/id/OIP.MO9kKuliwR3AlBcVAxgsTgHaHa?rs=1&pid=ImgDetMain">
			</div>
		<div class="details">
			<div>
				<h3>《作者》</h3>
			</div>
			<div>
				<ion-icon name="call-outline"></ion-icon>
				<span>练习时长两年半</span>
			</div>
			<div>
				<ion-icon name="mail-outline"></ion-icon>
				<span>为开发而生</span>
			</div>
		</div>
	</div>
</div>
</template>
<style scoped>
.card
{
	position: relative;
	width: 450px;
	height: 210px;
	background: rgba(255,255,255,0.1);
	backdrop-filter: blur(10px);
	box-shadow: 0 25px 25px rgba(0,0,0,0.1);
	border-radius: 20px;
	overflow: hidden;
	border-top: 1px solid rgba(255,255,255,0.5);
	transition: 1s;
	display: flex;
	justify-content: center;
	align-items: center;
}

.card:hover
{
	transform: scale(1,1.1);
	background: rgba(255,255,255,0.2); 
}

.card::before
{
	content:'';
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 60px;
	background: #bdbdbd;
}

.card .content
{
	position: relative;
	display: flex;
	align-items: center;
	margin-top: -80px;
	gap:20px;
}

img {
  
  top:0;
  left: 0;
  width:120px;
  height: 120px;
  object-fit: cover;
  border-radius: 50%;
  overflow: hidden;
}

.card.content.imgBox 
{
	position: relative;
	width: 120px;
	height: 120px;
	overflow: hidden;
	border-radius: 50%;
	box-shadow: 0 0 0 10px rgba(255,255,255,0.1);
}

.card .content .imgBox img
{
	position: absolute;
	top: 0;
	left: 0;
	width: 50%;
	height: 50%;
	object-fit: cover;
}

.card .content .details
{
	display: flex;
	flex-direction: column;
	gap:10px;
	color: #111;
}

.card .content .details div 
{
	display: flex;
	align-items: center;
	gap:10px;
}

.card .content .details h3
{
	font-weight: 600;
}

.card .content .details ion-icon
{
	font-size: 1.4em;
}
</style>
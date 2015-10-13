Graphics 1200,800,0,2
SetBuffer BackBuffer()
SeedRnd MilliSecs()

Global snakeImage = LoadImage("img\snake.png")
Global appleImage = LoadImage("img\apple.png")
Global tailImage = LoadImage("img\tail.png")
Global x = 300;start pos
Global y = 300
Global direction = 1 ;1-right;2-left;3-up;4-down
Global score = 0
Global sLife = 10
Global difficulty = 10;Int(Input("What difficulty do you want?[1-10]:    "))

Type TapplesData
	Field xpos,ypos
End Type

Type snakeData
	Field life
	Field xpos,ypos
End Type

Global snake.snakeData
Global apple.TapplesData

spawnApple()
While Not KeyHit(1)
	Cls()
	move()
	checkCollide()
	drawApple()
	drawTail()
	Text 10,10,"Score: " + score
	Flip
Wend

Function move()
	If KeyHit(205);right key
		If Not direction = 2 Then direction = 1
	ElseIf KeyHit(200);up key
		If Not direction = 4 Then direction = 3
	ElseIf KeyHit(203);left key
		If Not direction = 1 Then direction = 2
	ElseIf KeyHit(208);down key
		If Not direction = 3 Then direction = 4
	End If
	
	snake.snakeData = New snakeData
	snake\xpos = x:snake\ypos = y
	
	If direction = 1
		x=x+difficulty
	ElseIf direction = 2
		x=x-difficulty
	ElseIf direction = 3
		y=y-difficulty
	ElseIf direction = 4
		y=y+difficulty
	EndIf
	
	DrawImage snakeImage,x,y
	
	If x=<0
		x=1200
	ElseIf x>=1200
		x=0
	ElseIf y=<0
		y=800
	ElseIf y>=800
		y=0
	EndIf
End Function

Function spawnApple()
		apple.TapplesData = New TapplesData
		apple\xpos = Rnd(20,1180)
		apple\ypos = Rnd(20,780)
		apple.TapplesData = New TapplesData
		apple\xpos = Rnd(20,1180)
		apple\ypos = Rnd(20,780)
End Function

Function drawApple()
	Color 255,0,0
	For apple.TapplesData = Each TapplesData
		DrawImage appleImage,apple\xpos,apple\ypos
	Next
	Color 255,255,255
End Function

Function drawTail()
	For snake.snakeData  = Each snakeData
		snake\life = snake\life + 1
		If ImagesCollide(tailImage,snake\xpos,snake\ypos,0,snakeImage,x,y,0) Then End
		If direction = 1 Then
				DrawImage tailImage,snake\xpos-difficulty,snake\ypos
		Else If direction = 2 Then
				DrawImage tailImage,snake\xpos+difficulty,snake\ypos
		Else If direction = 3 Then
				DrawImage tailImage,snake\xpos,snake\ypos+difficulty
		Else If direction = 4 Then
				DrawImage tailImage,snake\xpos,snake\ypos-difficulty
		End If
		If snake\life > sLife Then
			Delete snake
		End If
	Next
End Function

Function checkCollide()
	For apple.TapplesData = Each TapplesData
		If ImagesCollide(snakeImage,x,y,0,appleImage,apple\xpos,apple\ypos,0)
			apple\xpos = Rnd(20,1180)
			apple\ypos = Rnd(20,780)
			score = score + 10
			sLife = sLife + 10
		End If
	Next
End Function
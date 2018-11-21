document.addEventListener("DOMContentLoaded", connect, false)
var socket;
function connect() {
    if (!window.WebSocket) {  
        window.WebSocket = window.MozWebSocket;  
    }  
    if (window.WebSocket) { 
        if(socket == null){
            socket = new WebSocket("ws://localhost:7788/websocket");
       }
        socket.onmessage = function(event) {




		     var self_value = document.getElementById("send_text");
             var ul = document.getElementById("chart_msg");
             var li = document.createElement("li");
	 
			 
			 
             var div1 = document.createElement("div");
             var div2 = document.createElement("div");    
             var span = document.createElement("span");
             span.style.paddingLeft="20px";
             span.style.marginLeft="20px";
             span.style.width="230px";
             span.style.overflow="hidden";
               span.style.marginTop="20px";
               span.innerHTML=event.data;
               var img = document.createElement("img");
               img.src="img/psb.jpg";
             div2.style.backgroundColor="white";
             div2.style.minHeight="70px";
             div2.style.width="250px";
             div2.style.borderRadius="10px";
             div2.className="chart_text";
             div1.className="head_img";
			 
			 
			 
             div1.appendChild(img);
             div2.appendChild(span);
             li.appendChild(div1);
             li.appendChild(div2);
             ul.appendChild(li);
        };  
        socket.onopen = function(event) {  
             
        };          
        socket.onclose = function(event) {  
  
        };  
    } else {  
        alert("Your browser does not support Web Socket.");  
    }  
}  
function sendContent() {
    var send_text = document.getElementById("send_text").value;
			 
			 
             var ul = document.getElementById("chart_msg");
             var li = document.createElement("li");
	 
			 
			 
             var div1 = document.createElement("div");
             var div2 = document.createElement("div");    
             var span = document.createElement("span");
             span.style.paddingLeft="20px";
             span.style.marginLeft="20px";
             span.style.width="230px";
             span.style.overflow="hidden";
               span.style.marginTop="20px";
               span.innerHTML=send_text;

               var img = document.createElement("img");
               img.src="img/sender.jpg";
             div2.style.backgroundColor="white";
             div2.style.minHeight="70px";
             div2.style.width="250px";
             div2.style.borderRadius="10px";
			
             div2.className="self_chart_text";
             div1.className="self_head_img";
			 
			 div1.appendChild(img);
             div2.appendChild(span);
             li.appendChild(div1);
             li.appendChild(div2);
             ul.appendChild(li);

            var self_text = document.getElementById("send_text");
            self_text.value="";
             socket.send(send_text);
}
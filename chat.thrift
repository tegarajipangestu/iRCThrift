typedef i32 int
service ChatService
{
        string createNickname(1:string name)
	bool joinChannel(1:string name, 2:string channel)
	bool leaveChannel(1:string name, 2:string channel)
	bool exitProgram(1:string name)
	bool sendMessage(1:string name, 2:string channel, 3:string message)
	string getMessage(1:string name)
}


from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# 用于存储接收到的信息的简单字典

stored_data = {}


# 处理POST请求，接收教室预约信息并存储
@app.route("/book_classroom", methods=["POST"])
def book_classroom():
    data = request.get_json()

    if not data:
        return jsonify({"error": "No data received"}), 400

    classroom_name = data.get("classroom_name")
    time = data.get("time")
    seat = data.get("seat")
    status = data.get("status")
                    

    # 检查教室是否已经存在，如果不存在则创建一个空字典
    if classroom_name not in stored_data:
        stored_data[classroom_name] = {}
        stored_data[classroom_name][time] = {}
    else:
        if time not in stored_data[classroom_name]:
            stored_data[classroom_name][time] = {}
    

    # 存储预约信息
    # stored_data[classroom_name][time] = {"seat": seat, "status": status}
    stored_data[classroom_name][time].update({seat : status})
    print("")
    print("stored_data from web:")
    print(stored_data)
    
    response = jsonify({"message": "Classroom booking added successfully"})
    # response.headers.add('Access-Control-Allow-Origin', '*') # 解决fetch，但没有用
    return response, 200


# 接收来自单片机的教室当前信息
@app.route('/update_classroom_status', methods=['POST'])
def update_classroom_status():
    data = request.json

    if not data:
        return jsonify({"error": "No data received"}), 400

    classroom_name = data.get('classroom_name')
    time = data.get("time")
    seat = data.get("seat")
    status = data.get('status')  # 是否有人
    brightness = data.get('brightness')
    people_count = data.get('people_count')  # 教室内人数改变
    
    # 更新教室信息
    if classroom_name not in stored_data:
        stored_data[classroom_name] = {}
        stored_data[classroom_name][time] = {}
    else:
        if time not in stored_data[classroom_name]:
            stored_data[classroom_name][time] = {}

    # stored_data[classroom_name][time].update({seat : status})
    stored_data[classroom_name][time].update({"brightness": brightness, "people_count": people_count})
    # stored_data[classroom_name]['seat_occupied'] = seat_occupied
    # stored_data[classroom_name]['people_count'] = people_count
    print("")
    print("stored_data from microcomputer:")
    print(stored_data)

    return jsonify({'message': 'Classroom status updated successfully'}), 200


# 处理GET请求，返回特定教室的预约信息
@app.route("/get_classroom_info/<classroom_name>", methods=["GET"])
def get_classroom_info(classroom_name):
    if classroom_name in stored_data:
        print("")
        print("stored_data[classroom_name]:")
        print(stored_data[classroom_name])
        return jsonify(stored_data[classroom_name]), 200
    else:
        print("error: Classroom not found")
        return jsonify({"error": "Classroom not found"}), 404


# 处理GET请求，返回特定教室特定时间的预约信息
@app.route("/get_booking_info/<classroom_name>/<time>", methods=["GET"])
def get_booking_info(classroom_name, time):
    if classroom_name in stored_data and time in stored_data[classroom_name]:
        print("")
        print("stored_data[classroom_name][time]:")
        print(stored_data[classroom_name][time])
        return jsonify(stored_data[classroom_name][time]), 200
    else:
        print({"error: Booking information not found"})
        return jsonify({"error": "Booking information not found"}), 404


if __name__ == "__main__":
    app.run(host="192.168.3.166", port = 5000, debug=True)

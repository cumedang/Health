async function fetchFoods(date) {
  try {
    const response = await fetch('http://localhost:8081/finddate?date=' + date);
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('음식을 가져오는 중 오류 발생:', error);
    throw error;
  }
}

document.getElementById("searchForm").addEventListener("submit", async function(event) {
  event.preventDefault(); // 폼 제출 동작 중단

  // 입력된 날짜 가져오기
  var date = document.getElementById("dateInput").value;

  if (!date) {
    alert("날짜를 입력해주세요!");
    return;
  }

  try {
    // 날짜에 대한 음식 데이터 가져오기
    const foods = await fetchFoods(date);

    // 검색 결과를 어떻게 보여줄지 정의합니다.
    // 이 예제에서는 간단히 검색 결과를 'searchResults' 요소에 표시합니다.
    var searchResultsElement = document.getElementById("searchResults");
    searchResultsElement.innerHTML = "<h3>날짜 검색 결과:</h3>";
    if (foods.length > 0) {
      var resultList = "<ul>";
      foods.forEach(food => {
        resultList += "<li><strong>음식 이름:</strong> " + food.name + "<br>";
        resultList += "<strong>시간대:</strong> " + food.moludi + "<br>";
        resultList += "<strong>칼로리:</strong> " + food.calories + "<br>";
        resultList += "<strong>단백질:</strong> " + food.protein + "<br>";
        resultList += "<strong>탄수화물:</strong> " + food.carbohydrate + "<br>";
        resultList += "<strong>지방:</strong> " + food.province + "<br>";
        resultList += "<strong>비타민:</strong> " + food.vitamin + "</li>";
      });
      resultList += "</ul>";
      searchResultsElement.innerHTML += resultList;
    } else {
      searchResultsElement.innerHTML += "<p>해당 날짜에 대한 데이터가 없습니다.</p>";
    }
  } catch (error) {
    console.error('검색 중 오류 발생:', error);
  }
});
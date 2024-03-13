 // REST API를 통해 음식 데이터를 가져오는 함수
 async function fetchFoods() {
  try {
      const response = await fetch('http://localhost:8081/foods'); // 음식 데이터를 가져올 REST API 엔드포인트를 입력하세요
      const data = await response.json();
      return data;
  } catch (error) {
      console.error('Error fetching foods:', error);
  }
}

// food div에 음식 정보 동적으로 추가
async function renderFoodItems() {
  const foods = await fetchFoods(); // 음식 데이터를 가져옴
  const foodDiv = document.getElementById('food');

  foods.forEach(food => {
      const foodItemDiv = document.createElement('div');
      foodItemDiv.classList.add('food-item');

      const imgBoxDiv = document.createElement('div');
      imgBoxDiv.id = 'imgbox'; // img를 감싸는 div에 id를 imgbox로 설정

      const imgElement = document.createElement('img');
      imgElement.src = food.link;
      imgElement.alt = food.name;

      const pElement = document.createElement('p');
      pElement.textContent = food.name;

      imgBoxDiv.appendChild(imgElement);
      imgBoxDiv.appendChild(pElement);

      foodItemDiv.appendChild(imgBoxDiv);

      foodDiv.appendChild(foodItemDiv);
  });
}

renderFoodItems(); // 음식 정보 렌더링

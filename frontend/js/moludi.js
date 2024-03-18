async function fetchFoods() {
  try {
      const response = await fetch('http://localhost:8081/foods');
      const data = await response.json();
      return data;
  } catch (error) {
      console.error('Error fetching foods:', error);
  }
}

async function renderFoodItems() {
  const foods = await fetchFoods();
  const foodDiv = document.getElementById('food');
  const searchInput = document.getElementById('searchInput').value.toLowerCase();

  foodDiv.innerHTML = ''; // 검색 결과를 업데이트하기 전에 이전 결과를 지웁니다.

  foods.forEach(food => {
    // 검색어가 포함된 결과만 보여줍니다.
    if (food.name.toLowerCase().includes(searchInput)) {
        const foodItemDiv = document.createElement('div');
        foodItemDiv.classList.add('food-item');

        const radioInput = document.createElement('input');
        radioInput.type = 'radio';
        radioInput.name = 'foodSelection'; // 라디오 그룹의 이름 설정
        radioInput.value = food.name; // 라디오 버튼의 값으로 음식의 이름 설정
        radioInput.classList.add('food-radio');

        const imgBoxDiv = document.createElement('div');
        imgBoxDiv.classList.add('img-box');

        const imgElement = document.createElement('img');
        imgElement.src = food.link;
        imgElement.alt = food.name;

        const foodName = document.createElement('p');
        foodName.classList.add('food-name');
        foodName.textContent = food.name;

        const foodDetails = document.createElement('div');
        foodDetails.classList.add('food-detail');

        const detailLabels = ['칼로리', '탄수화물', '단백빌', '지방', '비타민'];
        const detailValues = [food.calories, food.carbohydrate, food.protein, food.province, food.vitamin];

        detailLabels.forEach((label, index) => {
            const detailLabel = document.createElement('span');
            detailLabel.classList.add('detail-label');
            detailLabel.textContent = label + ': ';

            const detailValue = document.createElement('span');
            detailValue.classList.add('detail-value');
            detailValue.textContent = detailValues[index];

            const detailContainer = document.createElement('div');
            detailContainer.appendChild(detailLabel);
            detailContainer.appendChild(detailValue);

            foodDetails.appendChild(detailContainer);
        });

        imgBoxDiv.appendChild(imgElement);
        imgBoxDiv.appendChild(foodName);
        imgBoxDiv.appendChild(foodDetails);

        foodItemDiv.appendChild(radioInput); // 라디오 버튼을 foodItemDiv에 추가합니다.
        foodItemDiv.appendChild(imgBoxDiv);

        foodDiv.appendChild(foodItemDiv);
    }
});

}

function searchFunction() {
  renderFoodItems(); // 입력이 발생할 때마다 결과를 다시 렌더링합니다.
}

renderFoodItems(); // 페이지가 로드될 때 한 번 렌더링합니다.
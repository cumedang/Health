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
  //const foods = await fetchFoods();
  const foods = [
      {
          "name": "꿔바로우(200g)",
          "calories": 384,
          "link": "https://homecuisine.co.kr/files/attach/images/142/016/002/262e4ed7466e1378b4047e69cff4d7d0.JPG",
          "carbohydrate": "33.2",
          "protein": "22.5",
          "province": "17.4",
          "vitamin": "7"
      },
      {
          "name": "마라탕(210g)",
          "calories": 638,
          "link": "https://mashija.com/wp-content/uploads/2023/10/004-2.png",
          "carbohydrate": "36",
          "protein": "13.5",
          "province": "37.5",
          "vitamin": "15"
      }
  ];
  const foodDiv = document.getElementById('foods');
  const searchInput = document.getElementById('searchInput').value.toLowerCase();

  foodDiv.innerHTML = ''; // 검색 결과를 업데이트하기 전에 이전 결과를 지웁니다.

  foods.forEach(food => {
      // 검색어가 포함된 결과만 보여줍니다.
      if (food.name.toLowerCase().includes(searchInput)) {
          const foodItemDiv = document.createElement('div');
          foodItemDiv.classList.add('food-item');

          const radioInput = document.createElement('input');
          radioInput.type = 'radio';
          radioInput.name = 'food'; // 라디오 그룹의 이름 설정
          radioInput.value = food.name; // 라디오 버튼의 값으로 음식의 이름 설정
          radioInput.classList.add('food-radio');
          radioInput.id = `radio-${food.name}`; // 라디오 버튼 id 설정

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


          let foodRadio = document.querySelectorAll(".food-radio");

          const labelElement = document.createElement('label');
          labelElement.setAttribute('for', `radio-${food.name}`); // 라벨을 라디오 버튼과 연결
          foodItemDiv.appendChild(radioInput);
          foodItemDiv.appendChild(labelElement);
          labelElement.appendChild(imgBoxDiv);

          foodDiv.appendChild(foodItemDiv);
      }
  });

}

function searchFunction() {
  renderFoodItems(); // 입력이 발생할 때마다 결과를 다시 렌더링합니다.
}

renderFoodItems(); // 페이지가 로드될 때 한 번 렌더링합니다.

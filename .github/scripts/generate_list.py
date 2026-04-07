import os
import re
import subprocess
import urllib.request
import json
import time

def get_boj_title(problem_id):
    """solved.ac API를 이용해 백준 문제 한글 제목을 가져옵니다."""
    try:
        url = f"https://solved.ac/api/v3/problem/show?problemId={problem_id}"
        # 봇 차단 방지를 위해 User-Agent 헤더 추가
        req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
        with urllib.request.urlopen(req) as response:
            data = json.loads(response.read().decode())
            return data['titleKo']
    except Exception as e:
        return f"문제 {problem_id}"

def get_git_status(file_path):
    """마지막 커밋 메시지에서 상태 추출"""
    try:
        msg = subprocess.check_output(
            ["git", "log", "-1", "--pretty=%B", file_path]
        ).decode("utf-8").strip()
        
        if "PASS" in msg: return "✅ PASS"
        if "SUP" in msg: return "🙋 SUP"
        if "PENDING" in msg: return "⏳ PENDING"
        return "-"
    except:
        return "-"

def parse_file_name(file_name):
    # 정규식 업데이트: 괄호(이름) 부분이 없어도 통과하도록 수정!
    # 매칭 예시: BOJ_11723.java 또는 SWEA_2117(홈방범서비스).java
    pattern = r"([A-Z]+)_(\d+)(?:\((.*)\))?\.java"
    match = re.match(pattern, file_name)
    
    if match:
        p_code = match.group(1)
        num = match.group(2)
        name_in_file = match.group(3) # 괄호 안의 이름 (없으면 None)
        
        p_map = {"BOJ": "백준", "SWEA": "SWEA", "PRO": "프로그래머스"}
        platform = p_map.get(p_code, p_code)
        
        # 이름 결정 로직
        if p_code == "BOJ":
            name = get_boj_title(num) # 백준이면 API로 무조건 검색!
            time.sleep(0.1) # API 서버 무리 안 가게 살짝 쉬어줌
        else:
            # 백준이 아닌데 괄호 이름이 있으면 그거 쓰고, 없으면 번호를 이름으로 씀
            name = name_in_file if name_in_file else f"문제 {num}"
            
        return platform, num, name
    return None

def generate_list():
    dirs = sorted([d for d in os.listdir(".") if re.match(r'D\d{4}', d)], reverse=True)
    final_content = ""
    
    for d in dirs:
        final_content += f"\n### 📅 {d[1:3]}월 {d[3:5]}일 풀이\n"
        final_content += "| 플랫폼 | 번호 | 문제 이름 | 상태 | 링크 |\n"
        final_content += "| :--- | :--- | :--- | :--- | :--- |\n"
        
        files = sorted([f for f in os.listdir(d) if f.endswith(".java")])
        for f in files:
            parsed = parse_file_name(f)
            if parsed:
                platform, num, name = parsed
                status = get_git_status(os.path.join(d, f))
                link = f"[바로가기](https://www.acmicpc.net/problem/{num})" if platform == "백준" else "-"
                
                final_content += f"| {platform} | {num} | {name} | {status} | {link} |\n"
    
    with open("README.md", "r", encoding="utf-8") as f:
        readme = f.read()
        
    new_readme = re.sub(
        r".*?",
        f"\n{final_content}\n",
        readme, 
        flags=re.DOTALL
    )
    
    with open("README.md", "w", encoding="utf-8") as f:
        f.write(new_readme)

if __name__ == "__main__":
    generate_list()
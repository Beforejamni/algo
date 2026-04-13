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

def parse_file_name(file_name):
    """
    정규식 대신 split을 사용하여 영문, 하이픈, 공백이 포함된 파일명도 안전하게 분리합니다.
    예: SWEA_123_name-PASS.java -> 플랫폼: SWEA, 번호: 123, 이름: name-PASS
    """
    if not file_name.endswith(".java"):
        return None
        
    name_no_ext = file_name.replace(".java", "")
    # '_'를 기준으로 최대 2번만 쪼갭니다.
    parts = name_no_ext.split("_", 2)
    
    if len(parts) >= 2:
        p_code = parts[0] # BOJ, SWEA 등
        num = parts[1]    # 문제 번호
        # 이름이 있으면 쓰고, 없으면 번호를 이름으로 씀
        name_in_file = parts[2] if len(parts) > 2 else f"문제 {num}"
        
        p_map = {"BOJ": "백준", "SWEA": "SWEA", "PRO": "프로그래머스"}
        platform = p_map.get(p_code, p_code)
        
        if p_code == "BOJ":
            name = get_boj_title(num)
        else:
            name = name_in_file
            
        return platform, num, name
    return None

def get_git_status(file_path):
    """
    커밋 메시지 본문에서 상태 키워드를 찾습니다.
    """
    try:
        # git log -1 --pretty=%B "폴더/파일명.java"
        # 명령어 실행 시 경로 문제를 방지하기 위해 file_path를 그대로 전달
        msg = subprocess.check_output(
            ["git", "log", "-1", "--pretty=%B", file_path],
            stderr=subprocess.STDOUT
        ).decode("utf-8").upper() # 대문자로 변환하여 비교
        
        # 커밋 메시지 안에 PASS, SUP 등이 포함되어 있는지 확인
        if "PASS" in msg: return "✅ PASS"
        if "SUP" in msg: return "🙋 SUP"
        if "PENDING" in msg: return "⏳ PENDING"
        return "-"
    except Exception:
        return "-"

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
    
    # 80번 줄 근처: 여기서부터 수정!
    with open("README.md", "r", encoding="utf-8") as f:
        readme = f.read()
        
    # 직접 타이핑해서 채워야 하는 구간!
    start_tag = "<!-- PACKAGE_LIST_START -->"
    end_tag = "<!-- PACKAGE_LIST_END -->"
    
    # 봇이 찾아낼 패턴 (시작태그부터 종료태그까지)
    pattern = f"{re.escape(start_tag)}.*?{re.escape(end_tag)}"
    replacement = f"{start_tag}\n{final_content}\n{end_tag}"
    
    # 리드미에 태그가 둘 다 있을 때만 교체 진행
    if start_tag in readme and end_tag in readme:
        new_readme = re.sub(pattern, replacement, readme, flags=re.DOTALL)
    else:
        # 태그가 없으면 리드미 뒤에 붙여서 보존 (안전장치)
        new_readme = readme + "\n\n" + replacement
    
    with open("README.md", "w", encoding="utf-8") as f:
        f.write(new_readme)

if __name__ == "__main__":
    generate_list()